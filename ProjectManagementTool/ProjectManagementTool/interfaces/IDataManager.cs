using ProjectManagementTool.classes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.interfaces
{
    interface IDataManager
    {
        string ConnectionString
        {
            get;
            set;
        }

        User GetUser(int id);
        User GetUser(string email);
        User GetUser(string username, string password);
        void AddUser(User user);
        void AddProject(Project project);
        void UpdateUser(User user);
        DataTable GetAllUsers();
    }
}
