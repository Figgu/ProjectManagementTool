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
    /// Interaktionslogik für Issues.xaml
    /// </summary>
    public partial class SprintsWindow : Window
    {
        public SprintsWindow(User user, Project p)
        {
            InitializeComponent();
            this.currentUser = user;
            this.currentProject = p;
            LoadGUI();
        }

        private Kontext ktx = new Kontext();
        private User currentUser;
        private Project currentProject;
        private List<Sprint> sprints;

        private ListBoxItem GenerateListItem(Sprint s)
        {
            ListBoxItem item = new ListBoxItem
            {
                Content = "",//"Name: " + p.Name + ", Started On: " + p.ProjectStart.Day + "." + p.ProjectStart.Month + "." + p.ProjectStart.Year,
                FontSize = 30,
                Height = 50
            };
            return item;
        }

        private void LoadGUI()
        {
            lblProfile.Inlines.Clear();
            lblProfile.Inlines.Add(currentUser.Username);
            LoadSprintList();
            sprintList.MouseDoubleClick += sprintList_MouseDoubleClick;
        }

        private void Logout(object sender, RoutedEventArgs e)
        {
            LoginWindow login = new LoginWindow();
            login.Show();
            this.Close();
        }

        private void LoadSprintList()
        {
            sprints = ktx.GetAllSprintsFromProject(this.currentProject.Id);
            foreach (Sprint s in sprints)
            {
                sprintList.Items.Add(GenerateListItem(s));
            }
        }

        private void sprintList_MouseDoubleClick(object sender, RoutedEventArgs e)
        {
            if (sprintList.SelectedItem != null)
            {
                MessageBox.Show(sprintList.SelectedItem.ToString());
            }
        }
    }
}
