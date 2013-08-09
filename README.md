#Faster Than Pie

Faster Than Pie is the worlds most amazing FTP Client.
This particular program specializes in efficency and ease for the user.
Most FTP Client programs are visual SSH Clients that do FTP, this is pure FTP.
No modifications, no removals, this is purely UP/Down, which can be toggled on build.
The point of this program is for distribution to friends/family for FTP access to your own server.
You don't want your younger sister poking around on your server accidently removing /*
The purpose of this program is to give your box "user" access without configuring users in your server. 

##How it Works
    1. Set the Server Specifics
        - First set the host/username/password for the FTP Server
        - Then set the root directory for the server, this will be the folder they start in
            i. and can only move to subfolders of
        - Once those are set you can modify as you please
    2. Build and run the program
        - The program will populate the local and remote tables adding a "back" folder pinned to the 0th index
        - The user will browse and (depending on if upload is set) upload/download files from their dirs to the servers'
    3. Make Requests
        - A sample Node.js file with instructions on configuration will be supplied allowing for you to have web access
        - You can just FTP the requests to a specific file on your server if you do not wish to have webaccess on your server
