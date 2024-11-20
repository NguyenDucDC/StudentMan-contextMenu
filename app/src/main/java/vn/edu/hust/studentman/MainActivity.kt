package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf<StudentModel>()
  private var recentlyDeletedStudent: StudentModel? = null
  private var recentlyDeletedPosition: Int = -1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Khởi tạo danh sách sinh viên
    students.addAll(listOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      StudentModel("Trần Thị Bảo", "SV002"),
      StudentModel("Lê Hoàng Cường", "SV003"),
      StudentModel("Phạm Thị Dung", "SV004"),
      StudentModel("Đỗ Minh Đức", "SV005"),
      StudentModel("Vũ Thị Hoa", "SV006"),
      StudentModel("Hoàng Văn Hải", "SV007"),
      StudentModel("Bùi Thị Hạnh", "SV008"),
      StudentModel("Đinh Văn Hùng", "SV009"),
      StudentModel("Nguyễn Thị Linh", "SV010"),
      StudentModel("Phạm Văn Long", "SV011"),
      StudentModel("Trần Thị Mai", "SV012"),
      StudentModel("Lê Thị Ngọc", "SV013"),
      StudentModel("Vũ Văn Nam", "SV014"),
      StudentModel("Hoàng Thị Phương", "SV015"),
      StudentModel("Đỗ Văn Quân", "SV016"),
      StudentModel("Nguyễn Thị Thu", "SV017"),
      StudentModel("Trần Văn Tài", "SV018"),
      StudentModel("Phạm Thị Tuyết", "SV019"),
      StudentModel("Lê Văn Vũ", "SV020")
    ))

    studentAdapter = StudentAdapter(students, ::onEditClicked, ::onDeleteClicked)

    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddEditDialog()
    }
  }

  private fun showAddEditDialog(student: StudentModel? = null, position: Int = -1) {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
    val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_student_id)

    student?.let {
      editName.setText(it.studentName)
      editId.setText(it.studentId)
    }

    AlertDialog.Builder(this)
      .setTitle(if (student == null) "Add Student" else "Edit Student")
      .setView(dialogView)
      .setPositiveButton("Save") { _, _ ->
        val name = editName.text.toString()
        val id = editId.text.toString()

        if (student == null) {
          students.add(StudentModel(name, id))
        } else {
          students[position] = StudentModel(name, id)
        }
        studentAdapter.notifyDataSetChanged()
      }
      .setNegativeButton("Cancel", null)
      .show()
  }

  private fun onEditClicked(student: StudentModel, position: Int) {
    showAddEditDialog(student, position)
  }

  private fun onDeleteClicked(student: StudentModel, position: Int) {
    recentlyDeletedStudent = student
    recentlyDeletedPosition = position
    students.removeAt(position)
    studentAdapter.notifyItemRemoved(position)
    showUndoSnackbar()
  }

  private fun showUndoSnackbar() {
    Snackbar.make(findViewById(R.id.main), "Student deleted", Snackbar.LENGTH_LONG)
      .setAction("Undo") {
        recentlyDeletedStudent?.let {
          students.add(recentlyDeletedPosition, it)
          studentAdapter.notifyItemInserted(recentlyDeletedPosition)
        }
      }
      .show()
  }
}
