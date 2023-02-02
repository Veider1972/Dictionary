package ru.veider.dictionary.presentation.view.dictionary

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import org.koin.android.ext.android.getKoin
import ru.veider.dictionary.R
import ru.veider.dictionary.databinding.FragmentDictionaryBinding
import ru.veider.dictionary.model.data.AppState
import ru.veider.dictionary.presentation.view.hideKeyboard
import ru.veider.dictionary.presentation.viewmodel.DictionaryViewModel

class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheet: LinearLayout
    private val bottomSheetBehavior get() = from(bottomSheet)
    private lateinit var recyclerViewContainer: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchText: EditText
    private lateinit var searchButton: Button

    private lateinit var titleText: TextView

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel = getKoin().get<DictionaryViewModel>()

        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)

        bottomSheet = binding.bottomSheet
        recyclerViewContainer = binding.recyclerViewContainer
        recyclerView = binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }
        progressBar = binding.progressBar
        searchText = binding.searchText
        titleText = binding.titleText
        searchButton = binding.searchButton.apply {
            setOnClickListener {
                if (searchText.text.isNotEmpty()) {
                    requireContext().hideKeyboard(requireView())
                    viewModel.findWords(searchText.text.toString())
                    searchText.text.clear()
                }
            }
        }

        binding.fab.setOnClickListener {
            when (bottomSheetBehavior.state) {
                STATE_EXPANDED -> {
                    bottomSheetBehavior.state = STATE_COLLAPSED
                    searchText.text.clear()
                }
                else           -> {
                    bottomSheetBehavior.state = STATE_EXPANDED
                }
            }
        }

        viewModel.dictionaryData.observe(this.requireActivity()){ appState ->
            when (appState) {
                is AppState.Success -> {
                    progressBar.visibility = View.GONE
                    recyclerViewContainer.visibility = View.VISIBLE
                    appState.data?.let {
                        recyclerView.adapter = DictionaryAdapter(it)
                    }
                }
                is AppState.Loading -> {
                    recyclerViewContainer.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                is AppState.Error   -> {
                    progressBar.visibility = View.GONE
                    recyclerViewContainer.visibility = View.GONE
                    Toast.makeText(requireContext(),
                                   String.format(resources.getString(R.string.search_error),
                                                 appState.error.message
                                   ),
                                   Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }

        viewModel.searchedWord.observe(this.requireActivity()){title ->
            titleText.text = title
        }

        return binding.root
    }
}