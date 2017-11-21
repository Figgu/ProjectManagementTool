using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.classes
{
    //TODO loading
    public class Sprint
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

        public DateTime End
        {
            get { return end; }
            set { end = value; }
        }

        public Sprint(int id, DateTime start, DateTime end)
        {
            this.Id = id;
            this.Start = start;
            this.End = end;
        }

        private List<Issue> issues;

        public List<Issue> Issues
        {
            get { return issues; }
            set { issues = value; }
        }

    }
}
