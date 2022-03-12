package com.jordan.peopleofstarwars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jordan.peopleofstarwars.databinding.PeopleListItemBinding

//Class creation with GraphQL query list argument
class PeopleListAdapter(private val people: List<PeopleListQuery.Person>) :
    RecyclerView.Adapter<PeopleListAdapter.ViewHolder>() {

    var onItemClicked: ((PeopleListQuery.Person) -> Unit)? = null

    class ViewHolder(val binding: PeopleListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return people.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PeopleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = people[position]
        holder.binding.name.text = person.name ?: ""
        val specieFrom =
            ((person.species?.name) ?: "Human") + " from " + ((person.homeworld?.name) ?: "")
        holder.binding.specieHome.text = specieFrom

        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(person)
        }
    }
}