package com.example.movies.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.API.Resource
import com.example.movies.Activities.MainActivity
import com.example.movies.Adapters.ItemClickListener
import com.example.movies.Adapters.MovieAdapter
import com.example.movies.DataModel.Movie
import com.example.movies.R
import com.example.movies.databinding.FragmentListBinding


class ListFragment : Fragment() , ItemClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var movieAdapter: MovieAdapter
    private var _binding: FragmentListBinding?=null
    private lateinit var viewModel: MoviesViewModel
    private val binding get()=_binding!!
    private lateinit var listener: ItemClickListener
    private var allFabVisible=false
    private var turn=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        listener=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setUpRecyclerView()
        showProgressbar()
        viewModel.movieList1.observe(viewLifecycleOwner, Observer {
            response->
            when(response){
                is Resource.Success->{
                    response.data?.let{
                        res->
                        hideProgressbar()
                        movieAdapter.differ.submitList(res.MovieList)
                    }
                }
                is Resource.Error->{
                    hideProgressbar()
                    response.message?.let{m->
                        Log.w("MOVIES", "Error in fetching data")
                    }
                }
                is Resource.Loading->{
                    Log.w("MOVIES", "Loading.....")
                    showProgressbar()
                }
            }
        })

        binding.profileFab.setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToProfileFragment()
            findNavController().navigate(action)
        }

        binding.savedFab.setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToSavedMoviesFragment()
            findNavController().navigate(action)
        }

        binding.rcv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!binding.rcv.canScrollVertically(1)){
                    viewModel.movieList2.observe(viewLifecycleOwner, Observer {
                            response->
                        when(response){
                            is Resource.Success->{
                                response.data?.let{
                                        res->
                                    hideProgressbar()
                                    movieAdapter.differ.submitList(res.MovieList)
                                }
                            }
                            is Resource.Error->{
                                hideProgressbar()
                                response.message?.let{m->
                                    Log.w("MOVIES", "Error in fetching data")
                                }
                            }
                            is Resource.Loading->{
                                Log.w("MOVIES", "Loading.....")
                                showProgressbar()
                            }
                        }
                    })
                }
            }
        })
        //PROGRESS BAR NOT ADDED


    }
    private fun showProgressbar(){
        binding.pb.visibility=View.VISIBLE
    }
    private fun hideProgressbar(){
        binding.pb.visibility=View.INVISIBLE
    }

    private fun setUpRecyclerView(){
        movieAdapter= MovieAdapter(listener)
        binding.rcv.apply {
            adapter=movieAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }

    override fun onItemClick(view: View, movie: Movie) {
        viewModel.addToLocal(movie)
    }

    override fun reachedEnd(movie: Movie) {
        showProgressbar()
        if(turn%2==1){
            viewModel.movieList2.observe(viewLifecycleOwner, Observer {
                response->
                when(response){
                    is Resource.Success->{

                        response.data?.let{
                                res->
                            movieAdapter.differ.submitList(res.MovieList)
                            hideProgressbar()
                        }
                    }
                    is Resource.Error->{
                        hideProgressbar()
                        response.message?.let{m->
                            Log.w("MOVIES", "Error in fetching data")

                        }
                    }
                    is Resource.Loading->{
                        Log.w("MOVIES", "Loading.....")
                        showProgressbar()
                    }
                }
            })
        }
        else{
            viewModel.movieList1.observe(viewLifecycleOwner, Observer {
                    response->
                when(response){
                    is Resource.Success->{
                        response.data?.let{
                                res->
                            movieAdapter.differ.submitList(res.MovieList)
                            hideProgressbar()
                        }
                    }
                    is Resource.Error->{
                        response.message?.let{m->
                            Log.w("MOVIES", "Error in fetching data")
                            hideProgressbar()
                        }
                    }
                    is Resource.Loading->{
                        Log.w("MOVIES", "Loading.....")
                        showProgressbar()
                    }
                }
            })
        }
        turn++
    }
}