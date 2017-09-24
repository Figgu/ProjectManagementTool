using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.classes
{
    //TODO loading
    class Sprint
    {
        private int id;

        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        private DateTime start; 

        public DateTime Start
        {
            get { return start; }
            set { start = value; }
        }

        private DateTime end;

        public DateTime MyProperty
        {
            get { return end; }
            set { end = value; }
        }

        private List<Issue> issues;

        public List<Issue> Issues
        {
            get { return issues; }
            set { issues = value; }
        }

    }
}
