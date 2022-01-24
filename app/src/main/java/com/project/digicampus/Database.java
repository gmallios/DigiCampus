package com.project.digicampus;

public class Database {
    public static Database _instance;

    private Database()
    {

    }

    public static Database getInstance()
    {
        if (_instance == null)
        {
            _instance = new Database();
        }
        return _instance;
    }
}
