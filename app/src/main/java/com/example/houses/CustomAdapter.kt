package com.example.houses

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.core.Context
import com.google.firebase.database.core.Tag
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class CustomAdapter(private val mList: List<DataSnapshot>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    lateinit var context : android.content.Context
    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val numberView: TextView = itemView.findViewById(R.id.item_room_number)
        val locationView: TextView = itemView.findViewById(R.id.item_location)
        val linLayout : LinearLayout = itemView.findViewById(R.id.linear)
        val btn_delete :TextView = itemView.findViewById(R.id.delete)

    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.house_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val HouseViewModel = mList[position]

        var numRooms = HouseViewModel.children.elementAt(1).value.toString()
        var location = HouseViewModel.children.first().value.toString()

        // sets the text to the textview from our itemHolder class
        holder.numberView.setText(numRooms)

        // sets the text to the textview from our itemHolder class
        holder.locationView.text = location

        holder.linLayout.setOnClickListener{
            Toast.makeText(context,"pressed",Toast.LENGTH_LONG).show()

            val intent = Intent(context,House_Details::class.java)
            intent.putStringArrayListExtra("arr", arrayListOf(location,numRooms))
            context.startActivity(intent)


        }


        holder.btn_delete.setOnClickListener{
            //holder.btn_delete.setText(HouseViewModel.ref.toString())
            val dbRef = Firebase.database.getReferenceFromUrl(HouseViewModel.ref.toString())
            dbRef.removeValue()
        }


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }



}
