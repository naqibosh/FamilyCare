/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var ctx = document.getElementById("myPieChart");
const babysitterCount = parseInt(ctx.getAttribute("data-babysitter-count"));
const eldercareCount = parseInt(ctx.getAttribute("data-eldercare-count"));
    var myPieChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
        labels: ["Babysitter", "Eldercaretaker"], // Labels for the chart
                datasets: [{
                data: [babysitterCount, eldercareCount], // Counts from the database
                        backgroundColor: ['#4e73df', '#1cc88a'], // Custom colors for each segment
                        hoverBackgroundColor: ['#2e59d9', '#17a673'],
                        hoverBorderColor: "rgba(234, 236, 244, 1)",
            }],
        },
        options: {
                        maintainAspectRatio: false,
                        tooltips: {
                        backgroundColor: "rgb(255,255,255)",
                                bodyFontColor: "#858796",
                                borderColor: '#dddfeb',
                                borderWidth: 1,
                                xPadding: 15,
                                yPadding: 15,
                                displayColors: false,
                                caretPadding: 10,
            },
            legend: {
                                display: false
            },
            cutoutPercentage: 80,
        },
    });
