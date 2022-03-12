package com.jordan.peopleofstarwars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jordan.peopleofstarwars.databinding.VehicleListItemBinding


class VehicleListAdapter(private val vehicle: List<PersonDetailsQuery.Vehicle>) : RecyclerView.Adapter<VehicleListAdapter.ViewHolder>() {

    class ViewHolder(val binding: VehicleListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return vehicle.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            VehicleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehicle = vehicle.get(position)
        holder.binding.nameVehicle.text = vehicle.name ?: ""
    }
}