let isAsc = true;
let page = 1;
let pageSize = 3;
let totalPage = 0;

$(function () {
  $("#header").load("header.html");
  $("#content").load("body.html");
  $("#footer").load("footer.html");
});

// function homepage() {
//   $("#content").load("body.html");
// }
function viewEmployee() {
  $("#content").load("content.html", function () {
    buildTable();
  });
}
function buildTable() {
  $("tbody").empty();
  let nameSearch = $("#exampleInputAmount").val();
  $.ajax({
    type: "GET",
    url:
      "http://localhost:8080/api/v1/departments?isAsc=" +
      isAsc +
      "&nameSearch=" +
      nameSearch +
      "&position=" +
      page +
      "&pageSize=" +
      pageSize,
    dataType: "JSON",
    success: function (response) {
      listEmployee = response.departments;
      totalPage = response.totalPage;
      loadPaging();
      getListDepartment(listEmployee);
      checkSort();
    },
  });
}

function loadPaging() {
  $("#paging").empty();
  for (let i = 0; i < totalPage; i++) {
    if (i == page - 1) {
      $("#paging").append(
        `<button class="btn btn-primary" onclick="changePage(${i + 1})">${
          i + 1
        }</button>`
      );
    } else {
      $("#paging").append(
        `<button onclick="changePage(${i + 1})">${i + 1}</button>`
      );
    }
  }
}

function changePage(i) {
  page = i;
  buildTable();
}
function getAll() {
  page = 0;
  pageSize = 0;
  buildTable();
}

function getListDepartment(listEmployee) {
  for (let i = 0; i < listEmployee.length; i++) {
    $("#table").append(`
    <tr>
    
        <td>${listEmployee[i].name}</td>
        <td>
       
          <a class="edit" title="Edit" data-toggle="tooltip" onclick = "openUpdateModal(${listEmployee[i].id})"
            ><i class="fa-solid fa-pen"></i
          ></a>
          <a class="delete" title="Delete"  data-toggle="tooltip" onclick = "deleteDepartment(${listEmployee[i].id})"
            ><i class="fa-solid fa-trash"></i
          ></a>
        </td>
    </tr>
    `);
  }
}

function deleteDepartment(id) {
  var name = listEmployee.find((i) => i.id == id).name;
  // cho id va 1 ls , tifm ten cua emp tuong ung
  // for ls
  //  if(id = ls.[i].id)
  //
  var result = confirm("Want to " + name + " delete?");
  if (result == true) {
    console.log("click");
    $.ajax({
      type: "DELETE",
      url: "http://localhost:8080/api/v1/departments/" + id,
      // data:
      success: function (result) {
        buildTable();
        alert("Delete successfuly!");
      },
      error: function (rs) {
        alert("Delete failed");
      },
    });
  }
}
function addDepartment() {
  var department = {
    name: $("#name").val(),
  };
  $.ajax({
    type: "POST",
    url: "http://localhost:8080/api/v1/departments",
    data: JSON.stringify(department),
    contentType: "application/json",
    success: function (response) {
      alert("add ok");
      buildTable();
      closeModal();
    },
  });
}
function updateDepartment() {
  // openUpdateModal(id);
  var id = $("#id").val();
  var department = {
    id: $("#id").val(),
    name: $("#name").val(),
  };
  $.ajax({
    url: "http://localhost:8080/api/v1/departments/" + id,
    type: "PUT",

    data: JSON.stringify(department),
    contentType: "application/json",
    success: function (response) {
      alert("update ok");
      buildTable();
      closeModal();
    },
  });
}

var listEmployee = [];
var count = 0;
// khoi tao doi tuong
// function Employee(name, department, phone) {
//   this.id = ++count;
//   this.name = name;
//   this.department = department;
//   this.phone = phone;
// }
// khoi tao du lieu fix cung cho bang
function make_list_employee() {
  if (listEmployee.length == 0) {
    listEmployee.push(new Employee("Hoa", "sale", "099987212"));
    listEmployee.push(new Employee("Lan", "dev", "099007212"));
    listEmployee.push(new Employee("Linh", "content", "099922212"));
  }
}
// function buildTable() {
//   $("#table").empty();
//   make_list_employee();
//   console.log(listEmployee);
//   listEmployee.forEach(function (item) {
//     $("#table").append(
//       "<tr>" +
//         "<td>" +
//         item.name +
//         "</td>" +
//         "<td>" +
//         item.department +
//         "<td>" +
//         item.phone +
//         " <td>" +
//         '<a class="add" title="Add" data-toggle="tooltip"' +
//         '><i class="material-icons">&#xE03B;</i></a' +
//         " >" +
//         '<a class="edit" title="Edit" data-toggle="tooltip" onclick = openUpdateModal(' +
//         item.id +
//         ")" +
//         ' ><i class="fa-solid fa-pen"></i' +
//         " ></a>" +
//         '  <a class="delete" title="Delete" data-toggle="tooltip" onclick = "deleteEmployee(' +
//         item.id +
//         ",'" +
//         item.name +
//         "')\" " +
//         '><i class="fa-solid fa-trash"></i' +
//         " ></a>" +
//         " </td>" +
//         " </tr>"
//     );
//   });
// }
// function deleteEmployee(id, name) {
//   var result = confirm("Want to" + name + " delete?");
//   if (result) {
//     //Logic to delete the item
//     delete listEmployee[id - 1];
//     buildTable();
//   }
// }

function openModal() {
  $("#myModal").modal("show");
}
function closeModal() {
  $("#myModal").modal("hide");
}
function openUpdateModal(id) {
  var name = listEmployee.find((i) => i.id == id).name;
  $("#modal-title").text("Update department");
  $("#btn-primary").text("Update");
  $("#name").val(name);
  $("#id").val(id);

  openModal();
}

function openAddModal() {
  $("#modal-title").text("Add new Employee");
  $("#btn-primary").text("Save");
  $("#id").val("");
  $("#name").val("");
  $("#department").val("");
  $("#phone").val("");
  openModal();
}
// function addNewEmployee() {
//   if (
//     $("#name").val() == "" ||
//     $("#department").val() == "" ||
//     $("#phone").val() == ""
//   ) {
//     document.getElementById("error").style.display = "block";
//   } else {
//     // call api + data( chua obje) muon them
//     var dep = {
//       namee: $("#name").val(),
//     };
//     $.ajax({
//       type: "method",
//       url: "http://localhost:8080/api/v1/departments",
//       data: JSON.stringify(dep),
//       contentType: "application/json",
//       success: function (response) {},
//     });
//     listEmployee.push(
//       new Employee($("#name").val(), $("#department").val(), $("#phone").val())
//     );
//     closeModal();
//     buildTable();
//   }
// }
// function openUpdateModal(id) {
//   var emp;
//   // call api toi BE de lay ra employee cos id tuong ung
//   $.ajax({
//     type: "GET",
//     url: "http://localhost:8080/api/v1/departments/" + id,
//     // data: "data",
//     dataType: "JSON",
//     success: function (response) {
//       emp = response;
//     },
//   });
//   $("#modal-title").text("Update employee");
//   $("#btn-primary").text("Update");
//   $("#name").val(listEmployee[id - 1].name);
//   $("#department").val(listEmployee[id - 1].department);
//   $("#phone").val(listEmployee[id - 1].phone);
//   $("#id").val(listEmployee[id - 1].id);
//   openModal();
// }
function updateEmployeeDetail(id, name, department, phone) {
  listEmployee[id - 1].name = name;
  listEmployee[id - 1].department = department;
  listEmployee[id - 1].phone = phone;
}
function updateEmployee() {
  var id = $("#id").val();
  if (id == "") {
    addNewEmployee();
  } else {
    updateEmployeeDetail(
      $("#id").val(),
      $("#name").val(),
      $("#department").val(),
      $("#phone").val()
    );
    buildTable();
    closeModal();
  }
}
function save() {
  var id = $("#id").val();
  console.log(id);
  if (id == "") {
    addDepartment();
  } else {
    updateDepartment();
  }
}

function sortDown() {
  isAsc = false;
  buildTable();
  checkSort();
}

function sortUp() {
  isAsc = true;
  buildTable();
  checkSort();
}

function Paging2() {
  var text = $(".2").text();
  page = text;
  buildTable();
}
function Paging1() {
  var text = $(".1").text();
  page = text;
  buildTable();
}

function checkSort() {
  if (isAsc) {
    $("#up").attr("disabled", true);
    $("#down").attr("disabled", false);
  } else {
    $("#down").attr("disabled", true);
    $("#up").attr("disabled", false);
  }
}
