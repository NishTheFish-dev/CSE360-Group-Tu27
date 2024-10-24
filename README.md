# CSE360-Group-Tu27
Group Tuesday 27's Project Github Repository

##To-do:
- Improved/Nicer homepage once role is selected and logged in
- Helper functions for maintaining help articles, to be managed by Admins/Instructors
- Adding Database functionalities for Admins and Instructors


##Recent Updates:
- 1.5.0:
  - Added new user library dependency, H2
  - Added two new packages, services and database
  - Added two files to manage help articles, HelpArticle.java (under models) and HelpArticleService.java (under services)
  - Added two files to manage the database of articles, DatabaseHelper.java and BackupRestoreHelper.java (both under database)
  - Moved UserServices.java to the services package
- 1.2.2:
  - "Reset Password" functionality. An admin can now reset a user's password and send them a one-time password with an expiration date of 24 hours.
  -  Users are now correctly routed to the home page, or role selection page if they have multiple roles.
