package com.example.myapplication.util;

import com.example.myapplication.database.entity.Hike;

public class Memory {
    public static int hikeId;
    public static String hikeName;

    public static void setHikeId(int hikeId) {
        Memory.hikeId = hikeId;
    }
    public static void setHikename(String hikeName) {
        Memory.hikeName = hikeName;
    }
}
