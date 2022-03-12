package com.jordan.peopleofstarwars

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.apollographql.apollo3.exception.ApolloException
import com.jordan.peopleofstarwars.databinding.PeopleListFragmentBinding

class PeopleListFragment : Fragment() {
    //This is the binding object that will be used to access the people_list_fragment.xml
    private lateinit var binding: PeopleListFragmentBinding

    //This is the swipeRefreshLayout that will be used to refresh the list of people
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    val people = mutableListOf<PeopleListQuery.Person>()
    private val adapter = PeopleListAdapter(people)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PeopleListFragmentBinding.inflate(inflater)
        swipeRefreshLayout = binding.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            getPeople()
            swipeRefreshLayout.isRefreshing = false
            binding.error.visibility = View.GONE
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPeople()
    }

    //This function will get the people from the API
    private fun getPeople() {
        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(PeopleListQuery()).execute()
            } catch (e: ApolloException) {
                Log.d("PeopleList", "Failure", e)
                binding.error.visibility = View.VISIBLE
                null
            }

            val people = response?.data?.allPeople?.people?.filterNotNull()
            if (people != null && !response.hasErrors()) {
                val adapter = PeopleListAdapter(people)
                binding.title.text = (getString(R.string.app_name))
                binding.people.layoutManager = LinearLayoutManager(requireContext())
                binding.people.adapter = adapter
                binding.progress.visibility = View.GONE
                binding.loading.visibility = View.GONE
            } else {
                binding.progress.visibility = View.GONE
                binding.loading.visibility = View.GONE
                binding.error.visibility = View.VISIBLE
            }
        }

        adapter.onItemClicked = { people ->
            findNavController().navigate(
                PeopleListFragmentDirections.openPersonDetails(personID = people.id)
            )
        }
    }
}