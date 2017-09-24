﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.classes
{
    //TODO load methods
    class Project
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

        private string description;

        public string Description
        {
            get { return description; }
            set { description = value; }
        }

        private DateTime projectStart;

        public DateTime ProjectStart
        {
            get { return projectStart; }
            set { projectStart = value; }
        }

        public Project(String name,String description, DateTime start)
        {
            this.Name = name;
            this.Description = description;
            this.ProjectStart = start;
        }

        private List<User> users;

        public List<User> Users
        {
            get { return users; }
            set { users = value; }
        }

        private List<Group> groups;

        public List<Group> Groups
        {
            get { return groups; }
            set { groups = value; }
        }

        private List<Sprint> sprints;

        public List<Sprint> Sprint
        {
            get { return sprints; }
            set { sprints = value; }
        }
    }
}