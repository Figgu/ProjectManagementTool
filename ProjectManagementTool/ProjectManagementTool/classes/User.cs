using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.classes
{
    //TODO profile picture, Load methods for projects
    class User
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

        public User()
        {

        }

        public User(String username, String password, String email)
        {
            this.Username = username;
            this.Password = password;
            this.Email = email;
        }

        private List<Project> projects;

        public List<Project> Projects
        {
            get { return projects; }
            set { projects = value; }
        }

        private List<Issue> issues;

        public List<Issue> Issues
        {
            get { return issues; }
            set { issues = value; }
        }

    }
}
