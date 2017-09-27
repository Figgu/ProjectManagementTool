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
        void insertUser(User user);
        void updateUser(User user);
        DataTable selectAllUsers();
        User selectUser(string username, string password);
    }
}
