package com.syahrul.danamonfrontendtest.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syahrul.danamonfrontendtest.databinding.ItemUserAdminListBinding
import com.syahrul.danamonfrontendtest.room.UserRecord

class AdminUserListAdapter (
    private val currentUser: UserRecord,
    private val userList: ArrayList<UserRecord>,
    private val adapterListener: AdminUserListAdapterListener
) : RecyclerView.Adapter<AdminUserListAdapter.AdminUserListViewHolder>() {

    class AdminUserListViewHolder(val binding: ItemUserAdminListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminUserListViewHolder {
        return AdminUserListViewHolder(
            ItemUserAdminListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdminUserListViewHolder, position: Int) {
        val user = userList[position]

        val id = if(user.id == currentUser.id) "${user.id} (YOU)" else "${user.id}"
        holder.binding.tvId.text = id
        holder.binding.tvUsername.text = "${user.userName}"
        holder.binding.tvEmail.text = "${user.email}"

        val role = if(user.role == 1) "Admin" else "User"
        holder.binding.tvRole.text = role

        holder.binding.btnDelete.visibility = if(user.id == currentUser.id) GONE else VISIBLE
        holder.binding.btnEdit.visibility = if(user.id == currentUser.id) GONE else VISIBLE

        holder.binding.btnDelete.setOnClickListener {
            adapterListener.onDelete(user)
        }

        holder.binding.btnEdit.setOnClickListener {
            adapterListener.onEdit(user)
        }
    }

    override fun getItemCount() = userList.size

    interface AdminUserListAdapterListener {
        fun onDelete(user: UserRecord)
        fun onEdit(user: UserRecord)
    }

}