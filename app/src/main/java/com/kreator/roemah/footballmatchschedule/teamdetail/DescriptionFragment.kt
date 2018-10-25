package com.kreator.roemah.footballmatchschedule.teamdetail

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.kreator.roemah.footballmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DescriptionFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DescriptionFragment : Fragment(), AnkoComponent<Context>{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var deskripsi:TextView
    private var teamDescription: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        teamDescription = param1.toString()
        toast(param1.toString())


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View =with(ui){
        return linearLayout {
            orientation= LinearLayout.VERTICAL
            lparams(width= matchParent,height = wrapContent)
            deskripsi = textView(teamDescription){
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        deskripsi.text = teamDescription
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        fun newInstance(desc: String?) : DescriptionFragment {
            val fr = DescriptionFragment()
            val bundle = Bundle().apply {
                putString(ARG_PARAM1, desc)
            }

            fr.arguments = bundle
            return fr
        }

    }
}
