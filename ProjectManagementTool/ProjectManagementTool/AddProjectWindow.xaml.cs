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
    /// Interaktionslogik für AddProjectWindow.xaml
    /// </summary>
    public partial class AddProjectWindow : Window
    {
        private Kontext ktx = Kontext.GetInstance();
        private static AddProjectWindow instance = null;
        public AddProjectWindow()
        {
            InitializeComponent();
        }

        private void btnAddProject_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                DateTime date = dpProjectDate.SelectedDate ?? DateTime.MinValue;
                if (date != DateTime.MinValue)
                {
                    ktx.AddProject(new Project(txtProjectName.Text, "no description", date));
                    Main.RefreshProjects();
                }
                else
                {
                    MessageBox.Show("Select a valid date!");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Project already exists/Not a valid project.");
            }
        }
        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            instance = null;
        }

        public static void SetInstance(AddProjectWindow i)
        {
            instance = i;
        }

        public static AddProjectWindow GetInstance()
        {
            return instance;
        }
    }
}
