/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function updateTime() {
    const timeElement = document.getElementById('current-time');
    const now = new Date();
    const formattedTime = now.toLocaleTimeString(); // Displays time in HH:MM:SS format
    timeElement.textContent = formattedTime;
}
// Update time every second
setInterval(updateTime, 1000);
// Initialize time immediately on page load
updateTime();


