import { useEffect, React, useState } from 'react'
import axios from 'axios';

const ProfileForm = ({ user, vehicles }) => {

    const saveDetails = () => {

    }

    

  return (
    <>
    <div className="flex flex-wrap flex-row"></div>
    <div className="relative flex min-w-0 break-words w-full mb-6 ">
      
      <div className="flex-auto px-4 lg:px-10 py-10 pt-0">
        <form>
          <h6 className="text-blueGray-400 text-lg mt-3 mb-6 font-bold uppercase">
            Your Basic Information  
          </h6>
          <div className="flex flex-wrap">
            
            <div className="w-full lg:w-6/12 px-4">
              <div className="relative w-full mb-3">
                <label
                  className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                  
                >
                  Username
                </label>
                <input
                  type="text"
                  className=""
                  defaultValue={user.username}
                  value={user.username}
                />
              </div>
            </div>
            <div className="w-full lg:w-6/12 px-4">
              <div className="relative w-full mb-3">
                <label
                  className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                  
                >
                  Email address
                </label>
                <input
                  type="email"
                  className="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  defaultValue={user.contactEmail}
                  value={user.contactEmail}

                />
              </div>
            </div>
            <div className="w-full lg:w-6/12 px-4">
              <div className="relative w-full mb-3">
                <label
                  className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                  
                >
                  Contact Number
                </label>
                <input
                  type="text"
                  className="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  defaultValue={user.contactPhoneNumber}
                  value={user.contactPhoneNumber}
                />
              </div>
            </div>
            {/* <div className="w-full lg:w-6/12 px-4">
              <div className="relative w-full mb-3">
                <label
                  className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                  
                >
                  Password
                </label>
                <input
                  type="password"
                  className="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  value={user.password}
                />
              </div>
            </div> */}
            
          </div>

          <hr className="mt-6 border-b-1 border-blueGray-300" />
          

          <hr className="mt-6 border-b-1 border-blueGray-300" />
          
          {/* <button
            type="submit"
            onClick={saveDetails}
            className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded focus:outline-none focus:ring focus:ring-blue-300"
            >
            Update Profile
        </button> */}
        </form>
      </div>
    </div>
    </>
  )
}

export default ProfileForm