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
        void InsertUser(User user);
        void UpdateUser(User user);
        DataTable GetAllUsers();
    }
}
