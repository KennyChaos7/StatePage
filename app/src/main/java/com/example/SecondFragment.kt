package com.example

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.statepage.AbsStatePage
import com.statepage.StatePageManager
import com.statepage.simple.ReloadingStatePage

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var statePage: AbsStatePage

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root: View = inflater.inflate(R.layout.fragment_second, container, false)
        val frameLayout = root.findViewById<View>(R.id.frameLayout)

//        statePage = ReloadingStatePage(requireContext()) {}
        statePage = StatePageManager.defaultBind<ReloadingStatePage>(frameLayout){}

//        StatePageManager.bind(frameLayout, statePage)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
           StatePageManager.show(statePage)

           Handler().postDelayed(
                   Runnable {
                       StatePageManager.dismiss(statePage)
                   }, 5000
           )
        }
    }

    override fun onStop() {
        super.onStop()
        StatePageManager.unbind(statePage)
    }
}