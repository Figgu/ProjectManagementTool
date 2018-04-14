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
    /// Interaktionslogik für AddSprintWindow.xaml
    /// </summary>
    public partial class AddSprintWindow : Window
    {
        private Kontext ktx = Kontext.GetInstance();
        private static AddSprintWindow instance = null;
        private static Project currentProject = null;
        private static SprintsWindow caller = null;
        public AddSprintWindow(Project p, SprintsWindow s)
        {
            caller = s;
            currentProject = p;
            InitializeComponent();
        }

        private void btnAdd_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                DateTime sprintStart = dpSprintStart.SelectedDate ?? DateTime.MinValue;
                DateTime sprintEnd = dpSprintEnd.SelectedDate ?? DateTime.MinValue;
                if(sprintStart!=DateTime.MinValue && sprintEnd != DateTime.MinValue)
                {
                    ktx.AddSprint(currentProject.Id, sprintStart, sprintEnd);
                    caller.LoadSprintList();
                    this.Close();
                }
            }catch(Exception ex)
            {
                MessageBox.Show("Sprint could not be added.");
            }
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            instance = null;
        }

        public static void SetInstance(AddSprintWindow i)
        {
            instance = i;
        }

        public static AddSprintWindow GetInstance()
        {
            return instance;
        }
    }
}
