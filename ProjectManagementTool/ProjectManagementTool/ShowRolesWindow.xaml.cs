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
    /// Interaktionslogik für ShowRolesWindow.xaml
    /// </summary>
    public partial class ShowRolesWindow : Window
    {
        private Kontext ktx = Kontext.GetInstance();
        private static ShowRolesWindow instance = null;
        private List<Role> roles;
        private char charForNameWorkaround = 'x';
        public ShowRolesWindow()
        {
            InitializeComponent();
            LoadRoleList();
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            instance = null;
        }

        public static void SetInstance(ShowRolesWindow s)
        {
            instance = s;
        }

        public static ShowRolesWindow GetInstance()
        {
            return instance;
        }

        private void LoadRoleList()
        {
            roles = ktx.GetAllRoles();
            foreach (Role r in roles)
            {
                ListBoxRoles.Items.Add(GenerateListItem(r));
            }
        }

        private ListBoxItem GenerateListItem(Role r)
        {
            ListBoxItem item = new ListBoxItem
            {
                //char as a workaround because name cant be only a number
                Name = charForNameWorkaround + r.Id.ToString(),
                Content = "Name: " + r.Name + ", Description: " + r.Description,
                FontSize = 15,
                Height = 30
            };
            if (r.IsUnique)
            {
                item.Content += " (Unique)";
            }
            return item;
        }
    }
}
