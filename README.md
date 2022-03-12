
# MDT

A daily balance checking app where user can login, register and transfer any desired amount 
to selected payee.



## Demo
## Login
User can login only with valid username and password. Login button would be enabled after 
adding both inputs valid. To login user must have internet available. If internet un available
would show proper message. If provided credentials are not registered then can go to register.
![login](https://user-images.githubusercontent.com/1902728/158003096-3de48cf2-8217-4e0e-ac49-53779626251d.png)

## Registration: 
a simple username, password based registration. User would be taken back to login 
after successful registration. In the event of internet unavailablity -> proper message would
be shown by Toast
![registration](https://user-images.githubusercontent.com/1902728/158003105-4e18980d-decf-45dc-8824-99ee4a74a033.png)
## Dashbord
Shows users account no, accound holder name and remaining balance. 
Also shows all the transactions made each day. Click refresh button to refresh and observe
new balance,transactions data. Clicking log out would clear all user data and log out of the app.
![transactions](https://user-images.githubusercontent.com/1902728/158003106-1edfba14-9768-4e0b-a4a4-999519533ed0.png)
## Transfer
Click on payee from the drop down, write a valid amount, an optional descriotion and click Make transfer.
To transfer any balance. Click back button / back navigation menu on top bar to get back to the dashboard screen.
![maketransfer](https://user-images.githubusercontent.com/1902728/158003102-544e3baa-4a6f-49f8-8ea7-d0ca251894a1.png)


## Deployment

To deploy this project download code from `master` branch ,
run project on Android studio (tested on Android studio bumblbee), JDK 1.8 and emulator/physical device with minimum sdk level 21. Android name: Lollipop, version 5+

## Testing
Unit testing for all API available in com.sbr.mdt package /test directory. Run as test to check all unit tests.

The application full flow has been tested in Android 21, 28 and 30 version.


## API Reference

#### ALL GET and POST API from here:

https://github.com/sbrishta/mdt-homework-instruction#apis-usage




## Documentation
The app written in Kotlin.
Application follows MVVM architecture design pattern. 
Retrofit Library 2.9 version used to call API. 
Source code has a feature based structure: 

    
    src/

    feature/
        data/ 

        all model classes required to handle API integration with UI

        repository/
        Communicator between API and ViewModel

        ui/ 
         ViewModel : acts as controller of UI actions, requests data from repository,
         feeds data as Observable LiveData to UI

         Activity: Screens showcasing all data and user input actions

    /util
        all common files and helper classes

    /api
        retrofit api instance builder configuration
        all api interfaces    



