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
        public Sprint(int id, int projectId, DateTime start, DateTime end)
        {
            this.ProjectId = projectId;
            this.Id = id;
            this.Start = start;
            this.End = End;
        }

        private int projectId;

        public int ProjectId
        {
            get { return projectId; }
            set { projectId = value; }
        }

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

        private List<Issue> issues;

        public List<Issue> Issues
        {
            get { return issues; }
            set { issues = value; }
        }

    }
}
