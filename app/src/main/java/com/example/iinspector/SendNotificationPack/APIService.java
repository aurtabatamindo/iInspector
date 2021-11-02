package com.example.iinspector.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAYAqZC7w:APA91bE8bjADbCOrPrBXCjEJ4P2O4QyL4_H4gbIVVqWUXoGunkARnVZqYlWRpc1atx3cnK9WNZBnliUkWtFUQQ_zPAHgnCMgKM0H4a4EX4Z87jcHI6d46VbvDY0tNKMepTJeeyFR91dL" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

