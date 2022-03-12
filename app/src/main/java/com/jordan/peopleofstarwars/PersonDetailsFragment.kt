package com.jordan.peopleofstarwars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo3.exception.ApolloException
import com.jordan.peopleofstarwars.databinding.PersonDetailsFragmentBinding

class PersonDetailsFragment : Fragment() {

    //This is the binding object that will be used to access the person_details_fragment.xml
    private lateinit var binding: PersonDetailsFragmentBinding

    val args: PersonDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = PersonDetailsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            val response = try{
                apolloClient.query(PersonDetailsQuery(id = args.personID)).execute()
            } catch(e: ApolloException){
                return@launchWhenResumed
            }

            val person = response.data?.person
            val vehicle = person?.vehicleConnection?.vehicles?.filterNotNull()
            val adapter = vehicle?.let { VehicleListAdapter(it) }
            binding.title.text = person?.name
            binding.eyeColor.text = person?.eyeColor
            binding.hairColor.text = person?.hairColor
            binding.skinColor.text = person?.skinColor
            binding.birthYear.text = person?.birthYear
            binding.vehicles.adapter = adapter
        }
    }

}