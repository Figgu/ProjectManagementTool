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
    /// Interaktionslogik für IssuesWindow.xaml
    /// </summary>
    public partial class IssuesWindow : Window
    {
        private static IssuesWindow instance = null;
        public IssuesWindow(User user, Project p, Sprint s)
        {
            InitializeComponent();
            instance = this;
            this.currentUser = user;
            this.currentProject = p;
            this.currentSprint = s;
            LoadGUI();
        }

        private Kontext ktx = Kontext.GetInstance();
        private User currentUser;
        private Project currentProject;
        private Sprint currentSprint;
        private List<Issue> issues;
        private char charForNameWorkaround = 'x';

        private ListBoxItem GenerateListItem(Issue i)
        {
            ListBoxItem item = new ListBoxItem
            {
                Name = charForNameWorkaround + i.Id.ToString(),
                Content = i.Name + " (" + i.Status.ToString() + ")",
                FontSize = 30,
                Height = 50
            };
            return item;
        }

        private void LoadGUI()
        {
            lblProfile.Inlines.Clear();
            lblProfile.Inlines.Add(currentUser.Username);
            LoadIssueList();
            issueList.MouseDoubleClick += issueList_MouseDoubleClick;
        }

        private void Logout(object sender, RoutedEventArgs e)
        {
            Window w = null;
            for (int intCounter = App.Current.Windows.Count - 1; intCounter >= 0; intCounter--)
            {
                if (intCounter == 0)
                {
                    w = App.Current.Windows[intCounter];
                }
                else
                {
                    App.Current.Windows[intCounter].Close();
                }
            }
            LoginWindow login = new LoginWindow();
            login.Show();
            w.Close();
            this.Close();
        }

        public static void RefreshIssues()
        {
            instance.LoadIssueList();
        }

        private void LoadIssueList()
        {
            issueList.Items.Clear();
            issues = ktx.GetAllIssuesInSprint(this.currentSprint.Id,this.currentProject.Id);
            foreach (Issue i in issues)
            {
                issueList.Items.Add(GenerateListItem(i));
            }
        }

        private void issueList_MouseDoubleClick(object sender, RoutedEventArgs e)
        {
            if (issueList.SelectedItem != null)
            {
                ListBoxItem selectedListBoxItem = (ListBoxItem)issueList.SelectedItem;
                //workaround for the x added to the name
                Issue selectedIssue = issues.Find(i => i.Id == Convert.ToInt32(selectedListBoxItem.Name.Replace(charForNameWorkaround, ' ').Trim()));
                //TODO: OPEN WINDOW WITH ISSUE INFO HERE
                //IssuesWindow i = new IssuesWindow(currentUser, currentProject, selectedSprint);
                //i.Show();
            }
        }

        private void AddIssueClick(object sender, RoutedEventArgs e)
        {
            if (AddIssueWindow.GetInstance() == null)
            {
                AddIssueWindow r = new AddIssueWindow(this,currentSprint,currentProject);
                AddIssueWindow.SetInstance(r);
                r.Show();
            }
            else
            {
                AddIssueWindow.GetInstance().Focus();
            }
        }
    }
}
