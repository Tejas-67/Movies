package com.example.movies.UI

import android.content.ClipData.Item
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.Activities.MainActivity
import com.example.movies.Adapters.ItemClickListener
import com.example.movies.Adapters.MovieAdapter
import com.example.movies.Adapters.MovieAdapter2
import com.example.movies.DataModel.Movie
import com.example.movies.R
import com.example.movies.databinding.FragmentListBinding
import com.example.movies.databinding.FragmentSavedMoviesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavedMoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavedMoviesFragment : Fragment(), ItemClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var movieAdapter: MovieAdapter2
    private lateinit var listener: ItemClickListener

    private var _binding: FragmentSavedMoviesBinding?=null
    private val binding get()=_binding!!
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentSavedMoviesBinding.inflate(inflater, container, false)
        listener=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setUpRecyclerView()
        viewModel.allDataInLocal.observe(viewLifecycleOwner, Observer {
            res->
            movieAdapter.differ.submitList(res)
            Log.w("MOVIES", "${res.size}")
        })

    }

    override fun onItemClick(view: View, movie: Movie) {
        viewModel.deleteFromLocal(movie)

    }

    private fun setUpRecyclerView(){
        movieAdapter= MovieAdapter2(listener)

            binding.rcvSaved.adapter=movieAdapter
            binding.rcvSaved.layoutManager= LinearLayoutManager(activity)
    }

    override fun reachedEnd(movie: Movie) {

    }
}

