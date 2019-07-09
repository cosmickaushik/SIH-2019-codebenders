window.onload = function () {
  
 function addDataPoints1(){
  var chart1 = new CanvasJS.Chart("chart-container-one", {
    theme: "theme1",//theme1
    title:{
      text: ""              
    },
    animationEnabled: true,
    data: [{
    type: "pie",
    startAngle: 240,
    yValueFormatString: "##0.00\"%\"",
    indexLabel: "{label} {y}",   
      dataPoints: [
        { label: "Jan",  y:Number(document.getElementById('field1').value)},
        { label: "Feb", y:Number(document.getElementById("field2").value)},
        { label: "Mar", y:Number(document.getElementById("field3").value)},
        { label: "Apr",  y:Number(document.getElementById("field4").value)},
        { label: "May",  y:Number(document.getElementById("field5").value)},
        {label: "June", y:Number(document.getElementById("field6").value)},
        {label: "July", y:Number(document.getElementById("field7").value)},
        {label: "Aug", y:Number(document.getElementById("field8").value)},
        {label: "Sept", y:Number(document.getElementById("field9").value)},
      ]
    }
    ]
  });
  chart1.render();
}
  var renderButton2 = document.getElementById("renderButton2");
      renderButton2.addEventListener("click",addDataPoints1);
}
