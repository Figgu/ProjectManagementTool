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
    /// Interaktionslogik für AddIssueWindow.xaml
    /// </summary>
    public partial class AddIssueWindow : Window
    {
        private Kontext ktx = Kontext.GetInstance();
        private static AddIssueWindow instance = null;
        private static Project currentProject = null;
        private static Sprint currentSprint = null;
        private static IssuesWindow caller = null;

        public AddIssueWindow(IssuesWindow i, Sprint s, Project p)
        {
            caller = i;
            currentProject = p;
            currentSprint = s;
            InitializeComponent();
            Todo.IsChecked = true;
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            instance = null;
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        public static void SetInstance(AddIssueWindow i)
        {
            instance = i;
        }

        public static AddIssueWindow GetInstance()
        {
            return instance;
        }

        private void btnAdd_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                String name = txtBoxName.Text;
                String desc = txtBoxDesc.Text;
                IssueStatus Is = IssueStatus.TODO;
                if ((bool)InProcess.IsChecked)
                {
                    Is = IssueStatus.INPROCESS;
                }
                else if((bool)InReview.IsChecked)
                {
                    Is = IssueStatus.INREVIEW;
                }else if ((bool)Done.IsChecked)
                {
                    Is = IssueStatus.DONE;
                }
                ktx.AddIssue(currentProject.Id, currentSprint.Id, name, desc, Is);
                IssuesWindow.RefreshIssues();
                this.Close();

            }catch(Exception ex)
            {
                MessageBox.Show("Couldn't add Issue to Sprint");
            }
        }
    }
}
