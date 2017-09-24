using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.classes
{
    class Kontext
    {
        public Kontext()
        {

        }

        //TODO connectionString
        private string connectionString;
        public string ConnectionString
        {
            get { return connectionString; }
            set { connectionString = value; }
        }

        //TODO check if user is in the database
        public User GetPerson(String username, String password)
        {
            return new User();
        }

        //TODO add user to the db
        public User AddPerson(String username, String password, String email)
        {
            return new User(username,password,email);
        }


    }
}
