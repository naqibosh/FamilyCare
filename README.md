Family Care
===========

1. Project Name and Description
--------------------------------
- Project name: Family Care
- What it does: A small web app to let customers find and book caretakers. It also lets caretakers see jobs and staff manage users and bookings.

2. What You Need
-----------------
- Java JDK 8 (install Java 8 on your computer)
- NetBeans 8.2 or newer with Java EE support
- An application server (GlassFish 4.x recommended, Tomcat 8+ works too)
- A database (Oracle or MySQL). The project uses a JTA datasource named `jdbc/__TimerPool`.
- Oracle JDBC driver `ojdbc8.jar` (or your database's JDBC driver)
- Ant is included with NetBeans (no extra install needed)
- A web browser to open the site (Chrome, Firefox, Edge)

3. How to Install
------------------
1. Download and install Java JDK 8 from Oracle or an OpenJDK build.
2. Download and install NetBeans (8.2 or newer) that supports Java EE.
3. Download or copy this project folder to your computer.
4. Open NetBeans. Click `File` → `Open Project`. Select the `Family Care` folder and open it.
5. Add the database JDBC driver: either add `ojdbc8.jar` in NetBeans (Tools → Libraries) or copy the jar into `web/WEB-INF/lib`.
6. Configure an application server in NetBeans (GlassFish or Tomcat).
7. Create a JDBC datasource on the server named `jdbc/__TimerPool` and point it to your database.
8. Build the project: right-click the project in NetBeans and choose `Clean and Build`.

4. How to Run the Project
-------------------------
1. Start the application server (GlassFish or Tomcat) from NetBeans or its admin console.
2. In NetBeans, right-click the `Family Care` project and click `Run` or `Deploy`.
3. When deployed, open your browser and go to `http://localhost:8080/FamilyCare` (or the server URL shown by NetBeans).
4. The home page is `index.html`. Use the links to go to customer, caretaker, or staff pages.

Quick notes on pages:
- Customer login: `web/customer/login.html` (use the Login button)
- Customer booking: `web/customer/booking.html` (fill the form and submit)
- Caretaker login: `web/caretaker/login.jsp`
- Staff pages are under `web/staff` (manage bookings, customers, payments)

5. Project Structure
---------------------
- `nbproject/` - NetBeans project files (do not edit unless needed)
- `build.xml` - Ant build script used by NetBeans
- `src/conf/persistence.xml` - JPA settings and datasource name
- `src/java/` - Java source code. Main packages:
  - `customer/` - customer servlets and logic
  - `caretaker/` - caretaker servlets and pages
  - `staff/` - staff servlets and admin pages
  - `dbconn/` - database connection helpers
  - `utils/` - helper classes (session utilities etc.)
- `web/` - web pages, JSPs, CSS, JS, images
  - `WEB-INF/web.xml` - servlet mappings and app settings
  - `WEB-INF/lib/` - libraries (add JDBC driver here)

6. Main Features
-----------------
- Register as customer or staff.
- Log in and log out.
- Customers can make bookings for caretakers.
- Caretakers can view and update job status.
- Staff can manage customers, caretakers, bookings, and payments.

7. How to Use (Simple Examples)
--------------------------------
- Example 1: Register and book
  1. Open the site. Click `Register`.
  2. Fill the registration form and submit.
  3. Log in as the customer.
  4. Go to `Booking` page, fill details, and click `Book`.

- Example 2: Caretaker checks jobs
  1. Caretaker logs in from the caretaker login page.
  2. Open `View Jobs` or `Schedule` to see assigned jobs.
  3. Update job status when work is done.

- Example 3: Staff manages bookings
  1. Staff logs in from the staff login page.
  2. Open `Manage Bookings` to see all bookings.
  3. Accept, reject, or change booking status.

If something fails:
- Make sure the JDBC driver is in `WEB-INF/lib`.
- Make sure the server has the `jdbc/__TimerPool` resource.
- Check server logs in NetBeans output window for errors.

