package vn.edu.hust.studentman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.ContextMenu

class RecyclerViewContextMenuInfo(var position: Int) : ContextMenu.ContextMenuInfo

class StudentAdapter(
  private val students: List<StudentModel>,
  private val onEditClicked: (StudentModel, Int) -> Unit,
  private val onDeleteClicked: (StudentModel, Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

  class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context)
      .inflate(R.layout.layout_student_item, parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId

    // Thiết lập Context Menu khi nhấn giữ vào item
    holder.itemView.setOnCreateContextMenuListener { menu, _, _ ->
//      menu.setHeaderTitle("Options")

      // Thêm mục Edit
      val editItem = menu.add(0, R.id.context_edit, 0, "Edit")
      editItem.setOnMenuItemClickListener {
        // Xử lý khi chọn Edit: Mở activity để chỉnh sửa thông tin sinh viên
        onEditClicked(student, position)
        true
      }

      // Thêm mục Remove
      val removeItem = menu.add(0, R.id.context_remove, 1, "Remove")
      removeItem.setOnMenuItemClickListener {
        // Xử lý khi chọn Remove: Xóa sinh viên khỏi danh sách
        onDeleteClicked(student, position)
        true
      }
    }
  }
}


