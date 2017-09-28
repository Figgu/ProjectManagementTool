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

        User selectUser(int id);
        User selectUser(string email);
        User selectUser(string username, string password);
        void insertUser(User user);
        void updateUser(User user);
        DataTable selectAllUsers();
    }
}
