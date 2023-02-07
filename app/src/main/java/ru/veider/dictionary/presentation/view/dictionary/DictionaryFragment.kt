package ru.veider.dictionary.presentation.view.dictionary

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import kotlinx.coroutines.flow.*
import org.koin.android.ext.android.getKoin
import ru.veider.dictionary.R
import ru.veider.dictionary.databinding.FragmentDictionaryBinding
import ru.veider.dictionary.model.data.AppState
import ru.veider.dictionary.presentation.viewmodel.DictionaryViewModel

class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheet: LinearLayout
    private val bottomSheetBehavior get() = from(bottomSheet)
    private lateinit var recyclerViewContainer: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchText: EditText

    private lateinit var titleText: TextView

    private val viewModel = getKoin().get<DictionaryViewModel>()

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)

        bottomSheet = binding.bottomSheet
        recyclerViewContainer = binding.recyclerViewContainer
        recyclerView = binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }
        searchText = binding.searchText.apply {
            doOnTextChanged { text, _, _, _ ->
                viewModel.find(text.toString().trim())
            }
        }

        titleText = binding.titleText

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
                    appState.data?.let {
                        if (recyclerView.adapter == null){
                            recyclerView.adapter = DictionaryAdapter(it).apply {
                                setHasStableIds(true)
                            }
                        } else {
                            (recyclerView.adapter as DictionaryAdapter).updateList(it)
                        }

                    }
                }
                is AppState.Error   -> {
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