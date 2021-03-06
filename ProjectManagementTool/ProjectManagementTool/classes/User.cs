﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.classes
{
    public class User
    {
        private string username;

        public string Username
        {
            get { return username; }
            set { username = value; }
        }

        private string password;

        public string Password
        {
            get { return password; }
            set { password = value; }
        }

        private string email;

        public string Email
        {
            get { return email; }
            set { email = value; }
        }

        private int id;

        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        private byte[] picture;

        public byte[] Picture
        {
            get { return picture; }
            set { picture = value; }
        }

        public User()
        {

        }

        public User(String username, String password, String email)
        {
            this.Username = username;
            this.Password = password;
            this.Email = email;
        }

        public User(int id, String username, String password, String email, byte[] picture)
        {
            this.id = id;
            this.Username = username;
            this.Password = password;
            this.Email = email;
            this.Picture = picture;
        }

        public User(int id, String username, String password, String email)
        {
            this.id = id;
            this.Username = username;
            this.Password = password;
            this.Email = email;
        }
    }
}
