package com.example.workbalance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Pupulating email list
        val exampleList = generateDummyList(15)
        recyclerView.adapter = CustomEmailAdapter(exampleList)
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.setHasFixedSize(true)

        //Opening drawer on menu button click
        val menubtn = view.findViewById<ImageButton>(R.id.menu_button)
        menubtn.setOnClickListener {
            (activity as MainActivity).openDrawer(view);
        }

    }

    private fun generateDummyList(size : Int): List<EmailItem>{

        val list = ArrayList<EmailItem>()

        for (i in 0 until size) {
            val item = EmailItem(i)
            list += item
        }
        return list
    }
}