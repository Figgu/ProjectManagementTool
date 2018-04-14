using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.classes
{
    public class Issue
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

        private String description;

        public String Description
        {
            get { return description; }
            set { description = value; }
        }

        private IssueStatus status;

        public IssueStatus Status
        {
            get { return status; }
            set { status = value; }
        }

        public Issue(int id, string name, string description, IssueStatus status)
        {
            this.Id = id;
            this.Name = name;
            this.Description = description;
            this.Status = status;
        }
    }
}
