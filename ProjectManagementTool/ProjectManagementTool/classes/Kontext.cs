﻿using ProjectManagementTool.interfaces;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectManagementTool.classes
{
    class Kontext : IDataManager
    {
        private static Kontext instance = null;
        public static Kontext GetInstance() {
            if (instance==null)
            {
                instance = new Kontext();
            }
            return instance;
        }


        private Kontext ()
        {
            //Use 192.168.128.152 for connection in htl, use 212.152.179.117 for external use
            this.connectionString = "Provider = OraOLEDB.Oracle; OLEDB.NET=True;Data Source = 212.152.179.117:1521/ora11g;User Id = d5b03; Password=d5b;";
        }

        
        string connectionString;
        #region properties
        public string ConnectionString
        {
            get
            {
                return this.connectionString;
            }

            set
            {
                this.connectionString = value;
            }
        }

        User currentUser { get; set; }

        #endregion
        #region methods
        public void AddUser(User user)
        {
            OleDbCommand cmd = null;
            OleDbTransaction transaction = null;
            string commandText = "";
            using (OleDbConnection conn = new OleDbConnection(this.ConnectionString))
            {
                conn.Open();
                transaction = conn.BeginTransaction();
                commandText = "INSERT INTO User03(Username, Password, Email) VALUES (?, ?, ?)";
                cmd = new OleDbCommand(commandText, conn)
                {
                    Transaction = transaction
                };
                cmd.Parameters.AddWithValue("?", user.Username);
                cmd.Parameters.AddWithValue("?", user.Password);
                cmd.Parameters.AddWithValue("?", user.Email);
                cmd.ExecuteNonQuery();
                transaction.Commit();
            }
        }

        public void AddRole(Role role)
        {
            OleDbCommand cmd = null;
            string commandText = "";
            OleDbTransaction transaction = null;
            using(OleDbConnection conn = new OleDbConnection(this.connectionString))
            {
                conn.Open();
                transaction = conn.BeginTransaction();
                commandText = "Insert Into Role03(Name, Description,IsUnique) Values (?, ?, ?)";
                cmd = new OleDbCommand(commandText, conn)
                {
                    Transaction = transaction
                };
                cmd.Parameters.AddWithValue("?", role.Name);
                cmd.Parameters.AddWithValue("?", role.Description);
                //DB Trigger checks if "true" or "false", toString() returns "True" or "False"
                cmd.Parameters.AddWithValue("?", role.IsUnique.ToString().ToLowerInvariant());
                cmd.ExecuteNonQuery();
                transaction.Commit();
            }
        }

        public void AddProject(Project project)
        {
            OleDbCommand cmd = null;
            OleDbTransaction transaction = null;
            string commandText = "";
            using (OleDbConnection conn = new OleDbConnection(this.ConnectionString))
            {
                conn.Open();
                transaction = conn.BeginTransaction();
                commandText = "INSERT INTO Project03(Name, Description, ProjectBeginn) VALUES (?, ?, ?)";
                cmd = new OleDbCommand(commandText, conn)
                {
                    Transaction = transaction
                };
                cmd.Parameters.AddWithValue("?", project.Name);
                cmd.Parameters.AddWithValue("?", project.Description);
                cmd.Parameters.AddWithValue("?", project.ProjectStart);
                cmd.ExecuteNonQuery();
                transaction.Commit();
                ConnectProjectWithUser(project, currentUser);
            }
        }

        public void AddSprint(int projectId, DateTime start, DateTime end)
        {
            OleDbCommand cmd = null;
            OleDbTransaction transaction = null;
            string commandText = "";
            using (OleDbConnection conn = new OleDbConnection(this.ConnectionString))
            {
                conn.Open();
                transaction = conn.BeginTransaction();
                commandText = "INSERT INTO Sprint03(Projectid, StartDate, EndDate) VALUES (?, ?, ?)";
                cmd = new OleDbCommand(commandText, conn)
                {
                    Transaction = transaction
                };
                cmd.Parameters.AddWithValue("?", projectId);
                cmd.Parameters.AddWithValue("?", start);
                cmd.Parameters.AddWithValue("?", end);
                cmd.ExecuteNonQuery();
                transaction.Commit();
            }
        }

        public void AddIssue(int projectId, int sprintId, String name, String description, IssueStatus status)
        {
            OleDbCommand cmd = null;
            OleDbTransaction transaction = null;
            string commandText = "";
            using (OleDbConnection conn = new OleDbConnection(this.ConnectionString))
            {
                conn.Open();
                transaction = conn.BeginTransaction();
                commandText = "INSERT INTO Issue03(SprintProjectid, Sprintid, Name, Description, Status) VALUES (?, ?, ?, ?, ?)";
                cmd = new OleDbCommand(commandText, conn)
                {
                    Transaction = transaction
                };
                cmd.Parameters.AddWithValue("?", projectId);
                cmd.Parameters.AddWithValue("?", sprintId);
                cmd.Parameters.AddWithValue("?", name);
                cmd.Parameters.AddWithValue("?", description);
                cmd.Parameters.AddWithValue("?", status.ToString());
                cmd.ExecuteNonQuery();
                transaction.Commit();
            }
        }

        public void ConnectProjectWithUser(Project project, User user)
        {
            OleDbCommand cmd = null;
            OleDbTransaction transaction = null;
            string commandText = "";
            using (OleDbConnection conn = new OleDbConnection(this.ConnectionString))
            {
                conn.Open();
                transaction = conn.BeginTransaction();
                commandText = "INSERT INTO USERISINPROJECTWITHROLE03(UserID, ProjectID) VALUES (?, ?)";
                cmd = new OleDbCommand(commandText, conn)
                {
                    Transaction = transaction
                };
                Console.WriteLine(user.Id.ToString());
                cmd.Parameters.AddWithValue("?", user.Id);
                Console.WriteLine(GetCurrentProjectID(project).ToString());
                cmd.Parameters.AddWithValue("?", GetCurrentProjectID(project));
                cmd.ExecuteNonQuery();
                transaction.Commit();
            }
        }

        public void UpdateUser(User user)
        {
            currentUser = user;
            OleDbCommand cmd = null;
            OleDbTransaction transaction = null;
            string commandText = "";
            using (OleDbConnection conn = new OleDbConnection(this.ConnectionString))
            {
                conn.Open();
                transaction = conn.BeginTransaction();
                commandText = "UPDATE user03 SET username = ?, password = ?, email = ?, profilepicture = ? where userid = ?";
                cmd = new OleDbCommand(commandText, conn);
                cmd.Transaction = transaction;
                cmd.Parameters.AddWithValue("?", user.Username);
                cmd.Parameters.AddWithValue("?", user.Password);
                cmd.Parameters.AddWithValue("?", user.Email);
                cmd.Parameters.AddWithValue("?", user.Picture);
                cmd.Parameters.AddWithValue("?", user.Id);
                cmd.ExecuteNonQuery();
                transaction.Commit();
            }
        }

        public User GetCurrentUser()
        {
            return currentUser;
        }

        public int GetCurrentProjectID(Project project)
        {
            DataTable dt = new DataTable();
            OleDbDataAdapter da = null;
            using (OleDbConnection conn = new OleDbConnection(this.ConnectionString))
            {
                conn.Open();
                da = new OleDbDataAdapter("select projectid from project03 WHERE name = '" + project.Name + "' AND description = '" + project.Description + "'", conn);
                da.Fill(dt);
            }

            return Convert.ToInt32(dt.Rows[0][0]);
        }

        public User GetUser(int id)
        {
            DataTable dt = new DataTable();
            OleDbDataAdapter da = null;
            User user;
            using (OleDbConnection conn = new OleDbConnection(this.ConnectionString))
            {
                conn.Open();
                da = new OleDbDataAdapter("SELECT userid, username, password, email, profilepicture FROM User03 WHERE UserID = " + id, conn);
                da.Fill(dt);
            }
            if(dt.Rows[0][4] != DBNull.Value)
            {
                Console.WriteLine(dt.Rows[0][4]);
                user = new User(Convert.ToInt32(dt.Rows[0][0]), dt.Rows[0][1].ToString(), dt.Rows[0][2].ToString(), dt.Rows[0][3].ToString(), (byte[])dt.Rows[0][4]);
            }
            else
            {
                user = new User(Convert.ToInt32(dt.Rows[0][0]), dt.Rows[0][1].ToString(), dt.Rows[0][2].ToString(), dt.Rows[0][3].ToString());
            }
            currentUser = user;
            return user;
        }

        public DataTable GetAllUsers()
        {
            DataTable dt = new DataTable();
            OleDbDataAdapter da = null;
            using (OleDbConnection conn = new OleDbConnection(this.ConnectionString))
            {
                conn.Open();
                da = new OleDbDataAdapter("SELECT * FROM User03", conn);
                da.Fill(dt);
            }
            return dt;
        }

        public User GetUser(String username, String password)
        {
            DataTable dt = new DataTable();
            OleDbDataAdapter da = null;
            User user;
            using (OleDbConnection conn = new OleDbConnection(this.ConnectionString))
            {
                conn.Open();
                da = new OleDbDataAdapter("SELECT userid, username, password, email, profilepicture FROM User03 WHERE username = '" + username + "' AND password = '" + password + "'", conn);
                da.Fill(dt);
            }
            if (dt.Rows[0][4]!=DBNull.Value)
            {
                Console.WriteLine(dt.Rows[0][4].ToString());
                user = new User(Convert.ToInt32(dt.Rows[0][0]), dt.Rows[0][1].ToString(), dt.Rows[0][2].ToString(), dt.Rows[0][3].ToString(), (byte[]) dt.Rows[0][4]);
            }
            else
            {
                user = new User(Convert.ToInt32(dt.Rows[0][0]), dt.Rows[0][1].ToString(), dt.Rows[0][2].ToString(), dt.Rows[0][3].ToString());
            }
            currentUser = user;
            return user;
        }

        public User GetUser(string email)
        {
            DataTable dt = new DataTable();
            OleDbDataAdapter da = null;
            User user;
            using (OleDbConnection conn = new OleDbConnection(this.ConnectionString))
            {
                conn.Open();
                da = new OleDbDataAdapter("SELECT userid, username, password, email, profilepicture FROM User03 WHERE email = '" + email + "'", conn);
                da.Fill(dt);
            }

            if (dt.Rows[0][4] != DBNull.Value)
            {
                user = new User(Convert.ToInt32(dt.Rows[0][0]), dt.Rows[0][1].ToString(), dt.Rows[0][2].ToString(), dt.Rows[0][3].ToString(), (byte[])dt.Rows[0][4]);
            }
            else
            {
                user = new User(Convert.ToInt32(dt.Rows[0][0]), dt.Rows[0][1].ToString(), dt.Rows[0][2].ToString(), dt.Rows[0][3].ToString());
            }
            currentUser = user;
            return user;
        }

        public List<Project> GetAllProjectsOfUser(int userId)
        {
            DataTable dt = new DataTable();
            OleDbDataAdapter da = null;
            List<Project> projects = new List<Project>();
            using(OleDbConnection conn = new OleDbConnection(this.connectionString))
            {
                conn.Open();
                da = new OleDbDataAdapter("select distinct p.projectid, p.name, p.description, p.projectbeginn " +
                    "from userisinprojectwithrole03 up join project03 p on up.PROJECTID = p.PROJECTID where up.USERID = "+userId,conn);
                da.Fill(dt);
                foreach(DataRow r in dt.Rows)
                {
                    projects.Add(new Project(Convert.ToInt32(r[0]), r[1].ToString(), r[2].ToString(), Convert.ToDateTime(r[3])));
                }
            }
            return projects;
        }

        public List<Sprint> GetAllSprintsFromProject(int projectId)
        {
            DataTable dt = new DataTable();
            OleDbDataAdapter da = null;
            List<Sprint> sprints = new List<Sprint>();
            using (OleDbConnection conn = new OleDbConnection(this.connectionString))
            {
                conn.Open();
                da = new OleDbDataAdapter("select distinct sprintid, startdate, enddate from sprint03 where projectid = "+projectId, conn);
                da.Fill(dt);
                foreach (DataRow r in dt.Rows)
                {
                    sprints.Add(new Sprint(Convert.ToInt32(r[0]),Convert.ToDateTime(r[1]),Convert.ToDateTime(r[2])));
                }
            }
            return sprints;
        }
        
        public List<Issue> GetAllIssuesInSprint(int sprintId, int projectId)
        {
            DataTable dt = new DataTable();
            OleDbDataAdapter da = null;
            List<Issue> issues = new List<Issue>();
            using (OleDbConnection conn = new OleDbConnection(this.connectionString))
            {
                conn.Open();
                da = new OleDbDataAdapter("select distinct issueid, name, description, status from issue03 where sprintid = "+ sprintId +" AND sprintprojectid = " + projectId, conn);
                da.Fill(dt);
                foreach (DataRow r in dt.Rows)
                {
                    String isString = Convert.ToString(r[3]);
                    IssueStatus iS = IssueStatus.TODO;
                    switch (isString)
                    {
                        case "INPROCESS": iS = IssueStatus.INPROCESS;break;
                        case "INREVIEW": iS = IssueStatus.INREVIEW;break;
                        case "DONE": iS = IssueStatus.DONE;break;
                    }
                    issues.Add(new Issue(Convert.ToInt32(r[0]), Convert.ToString(r[1]), Convert.ToString(r[2]), iS));
                }
            }
            return issues;
        }
    

        public List<Role> GetAllRoles()
        {
            DataTable dt = new DataTable();
            OleDbDataAdapter da = null;
            List<Role> roles = new List<Role>();
            using (OleDbConnection conn = new OleDbConnection(this.connectionString))
            {
                conn.Open();
                da = new OleDbDataAdapter("select roleid, name, description, isunique from role03",conn);
                da.Fill(dt);
                foreach (DataRow r in dt.Rows)
                {
                    roles.Add(new Role(Convert.ToInt32(r[0]), Convert.ToString(r[1]), Convert.ToString(r[2]),Convert.ToBoolean(r[3])));
                }
            }
            return roles;
        }
        #endregion
    }

    
}
