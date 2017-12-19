using ProjectManagementTool.classes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ProjectManagementTool
{
    /// <summary>
    /// Interaction logic for Main.xaml
    /// </summary>
    public partial class Main : Window
    {
        private Kontext ktx = Kontext.GetInstance();
        private static User currentUser;
        private List<Project> projects;
        private char charForNameWorkaround = 'x';
        private static Main instance = null;

        public Main(User user)
        {
            InitializeComponent();
            currentUser = user;
            LoadGUI();
            instance = this;
        }

        private ListBoxItem GenerateListItem(Project p)
        {
            String test = ", Started On: ";
            if(p.ProjectStart > DateTime.Now)
            {
                test = ", Start On: ";
            }
            ListBoxItem item = new ListBoxItem
            {
                //workaround, as name cant only contain numbers for some reason
                Name = charForNameWorkaround+p.Id.ToString(),
                Content = "Name: " + p.Name + test + p.ProjectStart.Day + "." + p.ProjectStart.Month + "." + p.ProjectStart.Year,
                FontSize = 30,
                Height = 50
            };
            return item;
        }

        private void LoadGUI()
        {
            lblProfile.Inlines.Clear();
            lblProfile.Inlines.Add(currentUser.Username);
            LoadProjectList();
            projectList.MouseDoubleClick += projectList_MouseDoubleClick;
        }

        private void Logout(object sender, RoutedEventArgs e)
        {
            LoginWindow login = new LoginWindow();
            login.Show();
            this.Close();
        }

        private void LoadProjectList()
        {
            projectList.Items.Clear();
            projects = ktx.GetAllProjectsOfUser(currentUser.Id);
            foreach(Project p in projects)
            {
                projectList.Items.Add(GenerateListItem(p));
            }
            
        }
        private void projectList_MouseDoubleClick(object sender, RoutedEventArgs e)
        {
            if (projectList.SelectedItem != null)
            {
                ListBoxItem selectedListBoxItem = (ListBoxItem)projectList.SelectedItem;
                //workaround for the x added to the name
                Project selectedProject = projects.Find(p => p.Id == Convert.ToInt32(selectedListBoxItem.Name.Replace(charForNameWorkaround,' ').Trim()));
                SprintsWindow s = new SprintsWindow(currentUser, selectedProject);
                s.Show();
            }
        }

        private void lblProfile_Click(object sender, RoutedEventArgs e)
        {
            if (ProfileWindow.GetInstance() == null)
            {
                ProfileWindow profileWindow = new ProfileWindow();
                ProfileWindow.SetInstance(profileWindow);
                profileWindow.Show();
            }
            else
            {
                ProfileWindow.GetInstance().Focus(); 
            }
        }

        public static void SetUser(User u)
        {
            currentUser = u;
            instance.lblProfile.Inlines.Clear();
            instance.lblProfile.Inlines.Add(currentUser.Username);
        }

        public static void RefreshProjects()
        {
            instance.LoadProjectList();
        }

        private void AddProjectClick(object sender, RoutedEventArgs e)
        {
            if (AddProjectWindow.GetInstance() == null)
            {
                AddProjectWindow a = new AddProjectWindow();
                AddProjectWindow.SetInstance(a);
                a.Show();
            }
            else
            {
                AddProjectWindow.GetInstance().Focus();
            }
        }
    }
}
