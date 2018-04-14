using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.classes
{
    class Role
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

        private bool isUnique;

        public bool IsUnique
        {
            get { return isUnique; }
            set { isUnique = value; }
        }

        public Role(int id, string name, string description, bool isunique)
        {
            this.Id = id;
            this.Name = name;
            this.Description = description;
            this.IsUnique = isunique;
        }

        public Role(string name, string description, bool isunique)
        {
            this.Name = name;
            this.Description = description;
            this.IsUnique = isunique;
        }

    }
}
