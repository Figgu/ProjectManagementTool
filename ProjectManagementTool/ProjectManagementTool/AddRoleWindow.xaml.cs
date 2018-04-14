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
    /// Interaktionslogik für AddRoleWindow.xaml
    /// </summary>
    public partial class AddRoleWindow : Window
    {
        private Kontext ktx = Kontext.GetInstance();
        private static AddRoleWindow instance = null;
        public AddRoleWindow()
        {
            InitializeComponent();
        }
        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            instance = null;
        }

        public static void SetInstance(AddRoleWindow i)
        {
            instance = i;
        }

        public static AddRoleWindow GetInstance()
        {
            return instance;
        }

        private void ButtonCancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void ButtonCreate_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                Boolean isUnique = (bool)RadioButtonYes.IsChecked;
                ktx.AddRole(new Role(TextBoxName.Text, TextBoxDescription.Text, isUnique));
                this.Close();
            }catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
