# Authentication-Authorization-Spring-boot-3
Registration, Login using Access Token and Refresh Token, Role-based access

JWT stands for Json-Web Token.
It is stateless. The term stateless, means that we don't need to save it on the server side.
JWT is comprised of 3 parts -> Headers(algorithm used, type), Payload(data(username, expiry)), Signature(encoded headers, encode payload, and secret key).

A user registers to the application by entering the details like name, emailId(userName), password, phone number.
If the user is registered successfully, then the details are saved in the database, and the encoded password is saved in the database.

Now, when the user logs in to the application, by entering the Username and password. If the credentials are coorect and a match is found in the database, then an access token(JWT) is returned. (Note: This token is saved by the client(UI) on their side.)
Now when the user tries to access any resource(endpoint), this access token is sent with each request in the Headers. {Authorization, Bearer accessToken} ->The first one is the key and second is the value.
If the accessToken is valid then the resource is access, otherwise HttpStatus 403(Forbidden) is returned. (Note: 403(Forbidden) means that the user is logged in but does not have the permission to access the resource or endpoint). (Note: 401(Unauthorized) This means that the user is not logged in).

An accessToken has a expiry date attached to it. This means that after sometime the token will be expired, and the logged in user cannot access the resources now. So the user has to relogin again to get a fresh access token. 
This gives a bad exeprience to the user. So to overcome this, we also generate a refresh token is along with the access token  and give in response. Now, the client(UI) saved both the token. 
Now again with each request the access token is sent in Headers, if now the access token is expired, then the UI hits and endpoint with the refresh token in the body, to get a new access token. This way the user does not have to relogin again and again.

The refresh token also has expiry date, and if the refresh token is expired then the user has to relogin.
We need to save the refresh token in the database but not the access token.
