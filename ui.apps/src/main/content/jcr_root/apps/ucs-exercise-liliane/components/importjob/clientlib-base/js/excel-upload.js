$(document).ready(function () {
  $("#btnSubmit").prop("disabled", true);
  $("#xls").change(function () {
    $("#selectedFile").text(
      $("#xls")
        .val()
        .match(/[^\\/]*$/)[0]
    );
    $("#btnSubmit").prop("disabled", false);
    $(".result label").hide();
    $(".resultOk label").text("");
    $(".resultOk label").show();
    $(".resultSkipped label").text("");
    $(".resultSkipped label").show();
    $(".resultKo label").text("");
    $(".resultKo label").show();
  });

  $("#btnSubmit").click(function (event) {
    $(".result label").hide();
    $(".resultOk label").text("");
    $(".resultOk label").show();
    $(".resultSkipped label").text("");
    $(".resultSkipped label").show();
    $(".resultKo label").text("");
    $(".resultKo label").show();

    event.preventDefault();
    //validate input fields
    if ($("#xls").val().length > 1) {
      var filename = $("#xls").val();

      // Use a regular expression to trim everything before final dot
      var extension = filename.replace(/^.*\./, "");

      // If there is no dot anywhere in filename, we would have extension == filename
      if (extension == filename) {
        extension = "";
      } else {
        // if there is an extension, we convert to lower case
        // (N.B. this conversion will not effect the value of the extension
        // on the file upload.)
        extension = extension.toLowerCase();
      }

      if (extension != "csv") {
        alert("Only CSV formats are allowed!");
        return;
      }

      //stop submit the form, we will post it manually.

      // Get form
      var form = $("#fileUploadForm")[0];

      // Create an FormData object
      var data = new FormData(form);

      // for(let [name, value] of formData) {
      //     alert(`${name} = ${value}`); // key1 = value1, then key2 = value2
      // }

      // disabled the submit button
      $("#btnSubmit").prop("disabled", true);
      $("#xls").prop("disabled", true);

      $(".loading").removeClass("loading--hide").addClass("loading--show");

      var json;
      $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/bin/servlets/importjobstart2",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {
          json = JSON.parse(JSON.stringify(data));
          CheckImportJob(json);
        },
        error: function (e) {
          $(".result label").text("Import failed");
          $(".result label").show();
          $(".loading").removeClass("loading--show").addClass("loading--hide");
          $("#btnSubmit").prop("disabled", false);
          $("#xls").prop("disabled", false);
        },
      });
    } else {
      alert("Please, fill the mandatory fields");
      return;
    }
  });
});

var interval = 1000;
var timeout;
function CheckImportJob(json) {
  var jobData = {
    id: json["id"],
  };
  $.ajax({
    url: "/bin/servlets/importjobcheck",
    type: "post",
    dataType: "json",
    contentType: "application/json",
    data: JSON.stringify(jobData),
    success: function (data) {
      if (data["state"] == "SUCCEEDED") {
        clearTimeout(timeout);
        $(".result label").text("Progress: 100%");
        $(".result label").show();
        sleep(1000).then(() => {
          $(".result label").text("Activity report:");
          // $(".result label").hide();
          $(".resultOk label").text("- " + data["processLogOk"]);
          $(".resultOk label").show();
          $(".resultSkipped label").text("- " + data["processLogSkipped"]);
          $(".resultSkipped label").show();
          $(".resultKo label").text("- " + data["processLogKo"]);
          $(".resultKo label").show();
          $(".loading").removeClass("loading--show").addClass("loading--hide");
        });
      } else {
        var msg = "Progress: ";
        if (data["progressStep"] != undefined) {
          msg = msg + data["progressStep"];
        } else {
          msg = msg + "0";
        }
        $(".result label").text(msg);
        $(".result label").show();
        timeout = setTimeout(CheckImportJob, interval, json);
      }
    },
  });

  function sleep(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }
}
