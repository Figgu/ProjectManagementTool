using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.classes
{
    //TODO loading methods
    public class Group
    {
        private int id;

        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        private string name;

        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        private List<User> users;    

        public List<User> Users
        {
            get { return users; }
            set { users = value; }
        }

        private List<Project> projects;

        public List<Project> Projects
        {
            get { return projects; }
            set { projects = value; }
        }

        public Group(String name)
        {
            this.Name = name;
        }
    }
}
