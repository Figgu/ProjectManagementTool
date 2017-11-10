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
        private Kontext ktx = new Kontext();
        private User currentUser;
        private List<Project> projects;

        public Main(User user)
        {
            InitializeComponent();
            this.currentUser = user;
            LoadGUI();
        }

        private ListBoxItem GenerateListItem(Project p)
        {
            ListBoxItem item = new ListBoxItem
            {
                Name = Convert.ToString(p.Id),
                Content = "Name: " + p.Name + ", Started On: " + p.ProjectStart.Day + "." + p.ProjectStart.Month + "." + p.ProjectStart.Year,
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
            projects = ktx.GetAllProjectsOfUser(this.currentUser.Id);
            foreach(Project p in projects)
            {
                projectList.Items.Add(GenerateListItem(p));
            }
        }

        //not tested
        private void projectList_MouseDoubleClick(object sender, RoutedEventArgs e)
        {
            if (projectList.SelectedItem != null)
            {
                ListBoxItem selectedListBoxItem = (ListBoxItem)projectList.SelectedItem;
                Project selectedProject = projects.Find(p => p.Id == Convert.ToInt32(selectedListBoxItem.Name));
                SprintsWindow s = new SprintsWindow(currentUser, selectedProject);
                s.Show();
            }
        }

    }
}
