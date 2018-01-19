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

        private Kontext ktx = Kontext.GetInstance();
        private User currentUser;
        private Project currentProject;
        private List<Sprint> sprints;
        private char charForNameWorkaround = 'x';

        private ListBoxItem GenerateListItem(Sprint s)
        {
            ListBoxItem item = new ListBoxItem
            {
                //char as a workaround because name cant be only a number
                Name = charForNameWorkaround + s.Id.ToString(),
                Content = "Started On: " + s.Start.Day + "." + s.Start.Month + "." + s.Start.Year,
                FontSize = 30,
                Height = 50
            };
            if (s.End != null)
            {
                item.Content += ", Ended On: " + s.End.Day + "." + s.End.Month + "." + s.End.Year; 
            }
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

        public void LoadSprintList()
        {
            sprints = ktx.GetAllSprintsFromProject(this.currentProject.Id);
            sprintList.Items.Clear();
            foreach (Sprint s in sprints)
            {
                sprintList.Items.Add(GenerateListItem(s));
            }
        }

        private void sprintList_MouseDoubleClick(object sender, RoutedEventArgs e)
        {
            if (sprintList.SelectedItem != null)
            {
                ListBoxItem selectedListBoxItem = (ListBoxItem)sprintList.SelectedItem;
                //workaround for the x added to the name
                Sprint selectedSprint = sprints.Find(s => s.Id == Convert.ToInt32(selectedListBoxItem.Name.Replace(charForNameWorkaround, ' ').Trim()));
                IssuesWindow i = new IssuesWindow(currentUser, currentProject, selectedSprint);
                i.Show();
            }
        }

        private void AddSprintClick(object sender, RoutedEventArgs e)
        {
            if (AddSprintWindow.GetInstance() == null)
            {
                AddSprintWindow s = new AddSprintWindow(currentProject,this);
                AddSprintWindow.SetInstance(s);
                s.Show();
            }
            else
            {
                AddSprintWindow.GetInstance().Focus();
            }
        }
    }
}
