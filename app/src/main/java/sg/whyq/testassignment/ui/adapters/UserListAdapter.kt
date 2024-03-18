package sg.whyq.testassignment.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sg.whyq.testassignment.R
import sg.whyq.testassignment.databinding.ListItemUserListBinding
import sg.whyq.testassignment.ui.models.UserData
import sg.whyq.testassignment.utills.CommonFunctions

class UserListAdapter(val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var usersArrayList = ArrayList<UserData>()

    fun setData(usersArrayListTemp: List<UserData>) {
        usersArrayList = usersArrayListTemp as ArrayList<UserData>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ListItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersArrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bindData(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class ViewHolder(binding: ListItemUserListBinding) : RecyclerView.ViewHolder(binding.root) {

        var binding: ListItemUserListBinding

        init {
            this.binding = binding
        }

        fun bindData(position: Int) {

            val userDataModel = usersArrayList[position]

            val firstName = userDataModel.first_name
            val lastName = userDataModel.last_name
            val email = userDataModel.email
            val avatar = userDataModel.avatar

            binding.textViewUserName.text = "$firstName $lastName"
            binding.textViewUserEmail.text = email

            Glide.with(mContext)
                .load(avatar)
                .placeholder(R.drawable.ic_icon_user)
                .error(R.drawable.ic_icon_user)
                .into(binding.imageViewUser)

            binding.textViewUserEmail.setOnClickListener {
                CommonFunctions.sendEmailIntent(mContext, email)
            }
        }
    }
}