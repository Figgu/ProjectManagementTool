using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.classes
{
    class Role
    {
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

        private int id;

        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        public Role(int id, string name, bool isUnique, string description)
        {
            this.Id = id;
            this.Name = name;
            this.IsUnique = isUnique;
            this.Description = description;
        }

    }
}
